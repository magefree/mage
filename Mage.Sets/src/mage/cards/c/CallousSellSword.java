package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlDiedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CallousSellSword extends AdventureCard {

    private static final FilterPermanentOrPlayer filterSecondTarget = new FilterAnyTarget("any other target");

    private static final Hint hint = new ValueHint(
            "Creatures that died under your control this turn", CreaturesYouControlDiedCount.instance
    );

    public CallousSellSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{B}", "Burn Together", "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Callous Sell-Sword enters the battlefield with a +1/+1 counter on it for each creature that died under your control this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(0),
                        CreaturesYouControlDiedCount.instance, true
                ).setText("with a +1/+1 counter on it for each creature that died under your control this turn.")
        ).addHint(hint));

        // Burn Together
        // Target creature you control deals damage equal to its power to any other target. Then sacrifice it.
        this.getSpellCard().getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanentOrPlayer(filterSecondTarget).setTargetTag(2));
        this.getSpellCard().getSpellAbility().addEffect(new CallousSellSwordSacrificeFirstTargetEffect().concatBy("Then"));

        this.finalizeAdventure();
    }

    private CallousSellSword(final CallousSellSword card) {
        super(card);
    }

    @Override
    public CallousSellSword copy() {
        return new CallousSellSword(this);
    }
}

class CallousSellSwordSacrificeFirstTargetEffect extends OneShotEffect {

    CallousSellSwordSacrificeFirstTargetEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice it";
    }

    private CallousSellSwordSacrificeFirstTargetEffect(final CallousSellSwordSacrificeFirstTargetEffect effect) {
        super(effect);
    }

    @Override
    public CallousSellSwordSacrificeFirstTargetEffect copy() {
        return new CallousSellSwordSacrificeFirstTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game);
    }
}
