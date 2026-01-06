package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BeholdAndExileCost;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnExiledCardToHandEffect;
import mage.abilities.effects.keyword.BlightTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.BeholdType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionOfTheWeird extends CardImpl {

    public ChampionOfTheWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, behold a Goblin and exile it.
        this.getSpellAbility().addCost(new BeholdAndExileCost(BeholdType.GOBLIN));

        // Pay 1 life, Blight 2: Target opponent blights 2. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BlightTargetEffect(2), new PayLifeCost(1));
        ability.addCost(new BlightCost(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When this creature leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnExiledCardToHandEffect()));
    }

    private ChampionOfTheWeird(final ChampionOfTheWeird card) {
        super(card);
    }

    @Override
    public ChampionOfTheWeird copy() {
        return new ChampionOfTheWeird(this);
    }
}
