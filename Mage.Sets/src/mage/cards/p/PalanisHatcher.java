package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.DinosaurEggToken;
import mage.game.permanent.token.DinosaurVanillaToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PalanisHatcher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaurs");
    private static final FilterPermanent filterEgg = new FilterControlledPermanent(SubType.EGG, "an Egg");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.EGG, "you control one or more Eggs")
    );

    public PalanisHatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Other Dinosaurs you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // When Palani's Hatcher enters the battlefield, create two 0/1 green Dinosaur Egg creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DinosaurEggToken(), 2)));

        // At the beginning of combat on your turn, if you control one or more Eggs, sacrifice an Egg, then create a 3/3 green Dinosaur creature token.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new SacrificeControllerEffect(filterEgg, 1, "")
        ).withInterveningIf(condition);
        ability.addEffect(new CreateTokenEffect(new DinosaurVanillaToken()).concatBy(", then"));
        this.addAbility(ability);
    }

    private PalanisHatcher(final PalanisHatcher card) {
        super(card);
    }

    @Override
    public PalanisHatcher copy() {
        return new PalanisHatcher(this);
    }
}
