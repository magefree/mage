
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VarolzTheScarStriped extends CardImpl {

    public VarolzTheScarStriped(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VarolzTheScarStripedEffect()));

        // Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true))));
    }

    private VarolzTheScarStriped(final VarolzTheScarStriped card) {
        super(card);
    }

    @Override
    public VarolzTheScarStriped copy() {
        return new VarolzTheScarStriped(this);
    }
}

class VarolzTheScarStripedEffect extends ContinuousEffectImpl {

    VarolzTheScarStripedEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost";
    }

    VarolzTheScarStripedEffect(final VarolzTheScarStripedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    ScavengeAbility ability = new ScavengeAbility(new ManaCostsImpl<>(card.getManaCost().getText()));
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VarolzTheScarStripedEffect copy() {
        return new VarolzTheScarStripedEffect(this);
    }
}
