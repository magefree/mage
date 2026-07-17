package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PartnerVariantType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichelangeloTheHeart extends CardImpl {

    public MichelangeloTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Raid (the Fridge) -- At the beginning of your second main phase, if you attacked this turn, put a +1/+1 counter on target creature and create a Food token.
        Ability ability = new BeginningOfSecondMainTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        ).withInterveningIf(RaidCondition.instance);
        ability.addEffect(new CreateTokenEffect(new FoodToken()).concatBy("and"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(RaidHint.instance).withFlavorWord("Raid (the Fridge)"), new PlayerAttackedWatcher());

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private MichelangeloTheHeart(final MichelangeloTheHeart card) {
        super(card);
    }

    @Override
    public MichelangeloTheHeart copy() {
        return new MichelangeloTheHeart(this);
    }
}
