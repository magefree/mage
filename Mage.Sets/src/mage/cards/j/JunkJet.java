package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JunkToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JunkJet extends CardImpl {

    public JunkJet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Junk Jet enters the battlefield, create a Junk token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new JunkToken())));

        // {3}, Sacrifice another artifact: Double equipped creature's power until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new JunkJetAbility(),
                new GenericManaCost(3)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private JunkJet(final JunkJet card) {
        super(card);
    }

    @Override
    public JunkJet copy() {
        return new JunkJet(this);
    }
}

class JunkJetAbility extends OneShotEffect {

    JunkJetAbility() {
        super(Outcome.BoostCreature);
        staticText = "Double equipped creature's power until end of turn";
    }

    private JunkJetAbility(final JunkJetAbility effect) {
        super(effect);
    }

    @Override
    public JunkJetAbility copy() {
        return new JunkJetAbility(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = source.getSourcePermanentOrLKI(game);
        if (equipment == null) {
            return false;
        }
        Permanent equippedCreature = game.getPermanent(equipment.getAttachedTo());
        if (equippedCreature == null) {
            return false;
        }
        ContinuousEffect boost = new BoostTargetEffect(equippedCreature.getPower().getValue(), 0, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(equippedCreature, game));
        game.addEffect(boost, source);
        return true;
    }

}
