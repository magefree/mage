package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SoulSeizer extends CardImpl {

    public SoulSeizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.secondSideCardClazz = mage.cards.g.GhastlyHaunting.class;

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SoulSeizerEffect(), true, true);
        ability.setTriggerPhrase("When {this} deals combat damage to a player, ");
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private SoulSeizer(final SoulSeizer card) {
        super(card);
    }

    @Override
    public SoulSeizer copy() {
        return new SoulSeizer(this);
    }
}

class SoulSeizerEffect extends OneShotEffect {

    SoulSeizerEffect() {
        super(Outcome.GainControl);
        this.staticText = "you may transform it. If you do, attach it to target creature that player controls";
    }

    private SoulSeizerEffect(final SoulSeizerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.transform(source, game)) {
            return false;
        }
        Permanent attachTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        return attachTo != null && attachTo.addAttachment(permanent.getId(), source, game);
    }

    @Override
    public SoulSeizerEffect copy() {
        return new SoulSeizerEffect(this);
    }

}
