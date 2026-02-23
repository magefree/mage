package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceAttackingAloneCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GutterSkulker extends TransformingDoubleFacedCard {

    public GutterSkulker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{3}{U}",
                "Gutter Shortcut",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );

        // Gutter Skulker
        this.getLeftHalfCard().setPT(3, 3);

        // Gutter Skulker can't be blocked as long as it's attacking alone.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(Duration.WhileOnBattlefield),
                SourceAttackingAloneCondition.instance,
                "{this} can't be blocked as long as it's attacking alone"
        )));

        // Gutter Shortcut
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {3}{U}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{U}"));

        // Enchanted creature can't be blocked as long as it's attacking alone.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), GutterShortcutCondition.instance,
                "enchanted creature can't be blocked as long as it's attacking alone"
        )));

        // If Gutter Shortcut would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private GutterSkulker(final GutterSkulker card) {
        super(card);
    }

    @Override
    public GutterSkulker copy() {
        return new GutterSkulker(this);
    }
}

enum GutterShortcutCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && game.getCombat().attacksAlone()
                && game.getCombat().getAttackers().contains(permanent.getAttachedTo());
    }
}
