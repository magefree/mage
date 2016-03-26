/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.keyword;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.permanent.token.ClueArtifactToken;

/**
 *
 * @author LevelX2
 */
public class InvestigateEffect extends CreateTokenEffect {

    public InvestigateEffect() {
        super(new ClueArtifactToken());
        this.staticText = "Investigate. <i>(Put a colorless Clue artifact token onto the battlefield with \"{2}, Sacrifice this artifact: Draw a card.\")</i>";
    }

    public InvestigateEffect(final InvestigateEffect effect) {
        super(effect);
    }

    @Override
    public InvestigateEffect copy() {
        return new InvestigateEffect(this);
    }
}