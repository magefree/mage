package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.WaylayToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DionBahamutsDominant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KNIGHT, "");

    public DionBahamutsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.b.BahamutWardenOfLight.class;

        // Dragonfire Dive -- During your turn, Dion and other Knights you control have flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, {this}"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter),
                MyTurnCondition.instance, "and other Knights you control have flying"
        ));
        this.addAbility(ability.withFlavorWord("Dragonfire Dive"));

        // When Dion enters, create a 2/2 white Knight creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WaylayToken())));

        // {4}{W}{W}, {T}: Exile Dion, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DionBahamutsDominant(final DionBahamutsDominant card) {
        super(card);
    }

    @Override
    public DionBahamutsDominant copy() {
        return new DionBahamutsDominant(this);
    }
}
