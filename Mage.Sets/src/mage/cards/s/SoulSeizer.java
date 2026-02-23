package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SoulSeizer extends TransformingDoubleFacedCard {

    public SoulSeizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{3}{U}{U}",
                "Ghastly Haunting",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );

        // Soul Seizer
        this.getLeftHalfCard().setPT(1, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.
        TriggeredAbility ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SoulSeizerEffect(), true, true);
        ability.setTriggerPhrase("When {this} deals combat damage to a player, ");
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.getLeftHalfCard().addAbility(ability);

        // Ghastly Haunting
        // Enchant creature
        Target auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));
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
