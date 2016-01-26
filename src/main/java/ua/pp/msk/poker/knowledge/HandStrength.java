/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.pp.msk.poker.knowledge;

import ua.pp.msk.poker.rules.Hand;
import ua.pp.msk.poker.stat.GameStage;

/**
 *
 * @author Maksym Shkolnyi aka maskimko
 */
public interface HandStrength extends KnowledgeConsts{

    /**
     * Calculates winning chance in current game in percents
     * @param hand
     * @param gs
     * @return Percent of winning chance
     */
    public float estimate(Hand hand, GameStage gs);
    
    /**
     * Calculates chance to win with the particular hand (all games generally)
     * E.G. Royal Flush is very rare combination, hence chance to win with this combination is extremely small.
     * @param hand
     * @param gs
     * @return chance to win in percents.
     */
    public float chance(Hand hand, GameStage gs);
    /**
     * Calculates relative hand strength for current game stage
     * @param hand
     * @param gs
     * @return Strength in percents;
     */
    public float strength(Hand hand, GameStage gs);
}
