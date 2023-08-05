package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;

import java.util.UUID;

/**
 * @author arcox
 */
public final class RadhaHeartOfKeld extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("play lands");

    public RadhaHeartOfKeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as it's your turn, Radha, Heart of Keld has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "As long as it's your turn, {this} has first strike."
        )).addHint(MyTurnHint.instance));

        // You may look at the top card of your library any time, and you may play lands from the top of your library.
        LookAtTopCardOfLibraryAnyTimeEffect lookEffect = new LookAtTopCardOfLibraryAnyTimeEffect();
        lookEffect.setText("You may look at the top card of your library any time");
        PlayTheTopCardEffect playEffect = new PlayTheTopCardEffect(TargetController.YOU, filter, false);
        playEffect.setText(", and you may play lands from the top of your library");

        SimpleStaticAbility lookAndPlayAbility = new SimpleStaticAbility(lookEffect);
        lookAndPlayAbility.addEffect(playEffect);
        this.addAbility(lookAndPlayAbility);

        // 4RG: Radha gets +X/+X until end of turn, where X is the number of lands you control.
        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);
        BoostSourceEffect bse = new BoostSourceEffect(controlledLands, controlledLands, Duration.EndOfTurn, true);
        bse.setText("Radha gets +X/+X until end of turn, where X is the number of lands you control");
        this.addAbility(new SimpleActivatedAbility(bse, new ManaCostsImpl<>("{4}{R}{G}")));
    }

    private RadhaHeartOfKeld(final RadhaHeartOfKeld card) {
        super(card);
    }

    @Override
    public RadhaHeartOfKeld copy() {
        return new RadhaHeartOfKeld(this);
    }
}
