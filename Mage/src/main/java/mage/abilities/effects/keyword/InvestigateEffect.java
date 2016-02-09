/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class InvestigateEffect extends OneShotEffect {

    public InvestigateEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Investigate. <i>(Put a colorless Clue artifact token onto the battlefield with \"{2}, Sacrifice this artifact: Draw a card.\")</i>";
    }

    public InvestigateEffect(final InvestigateEffect effect) {
        super(effect);
    }

    @Override
    public InvestigateEffect copy() {
        return new InvestigateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

class ClueArtifactToken extends Token {

    ClueArtifactToken() {
        super("Clue", "colorless Clue artifact token onto the battlefield with \"{2}, Sacrifice this artifact: Draw a card.\"");
        this.setOriginalExpansionSetCode("SOI");
        this.cardType.add(CardType.ARTIFACT);

        // {2}, Sacrifice this artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        SacrificeSourceCost cost = new SacrificeSourceCost();
        cost.setText("Sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(ability);
    }
}
