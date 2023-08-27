package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class OgreChitterlord extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent(SubType.RAT, "each Rat you control");
    private static final FilterControlledPermanent filterCondition =
            new FilterControlledPermanent(SubType.RAT, "you control five or more Rats");
    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(filterCondition, ComparisonType.OR_GREATER, 5);

    public OgreChitterlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        //  Whenever Ogre Chitterlord enters the battlefield or attacks, create two 1/1 black Rat creature tokens with "This creature can't block." Then if you control five or more Rats, each Rat you control gets +2/+0 until end of turn.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new RatCantBlockToken(), 2),
                false, "whenever {this} enters the battlefield or attacks, ",
                new EntersBattlefieldTriggeredAbility(null),
                new AttacksTriggeredAbility(null)
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false)
                ),
                condition
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private OgreChitterlord(final OgreChitterlord card) {
        super(card);
    }

    @Override
    public OgreChitterlord copy() {
        return new OgreChitterlord(this);
    }
}
