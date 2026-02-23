package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ShadowfaxLordOfHorses extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HORSE, "Horses");
    private static final FilterCard filter2 = new FilterCreatureCard("creature card with lesser power");

    static {
        filter.add(ShadowfaxLordOfHorsesPredicate.instance);
    }

    public ShadowfaxLordOfHorses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Horses you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever Shadowfax, Lord of Horses attacks, you may put a creature card with lesser power from your hand onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter2, false, true, true)));
    }

    private ShadowfaxLordOfHorses(final ShadowfaxLordOfHorses card) {
        super(card);
    }

    @Override
    public ShadowfaxLordOfHorses copy() {
        return new ShadowfaxLordOfHorses(this);
    }
}

enum ShadowfaxLordOfHorsesPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(power -> input.getObject().getPower().getValue() < power)
                .isPresent();
    }
}
