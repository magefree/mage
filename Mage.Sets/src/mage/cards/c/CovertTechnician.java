package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovertTechnician extends CardImpl {

    public CovertTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Ninjutsu {1}{U}
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever Covert Technician deals combat damage to a player, you may put an artifact card with mana value less than or equal to that damage from your hand onto the battlefield.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CovertTechnicianEffect(), false, true
        ));
    }

    private CovertTechnician(final CovertTechnician card) {
        super(card);
    }

    @Override
    public CovertTechnician copy() {
        return new CovertTechnician(this);
    }
}

class CovertTechnicianEffect extends OneShotEffect {

    CovertTechnicianEffect() {
        super(Outcome.Benefit);
        staticText = "you may put an artifact card with mana value less than " +
                "or equal to that damage from your hand onto the battlefield";
    }

    private CovertTechnicianEffect(final CovertTechnicianEffect effect) {
        super(effect);
    }

    @Override
    public CovertTechnicianEffect copy() {
        return new CovertTechnicianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) getValue("damage");
        FilterCard filter = new FilterArtifactCard("artifact card with mana value " + damage + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, damage + 1));
        return new PutCardFromHandOntoBattlefieldEffect(filter).apply(game, source);
    }
}
