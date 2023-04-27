
package mage.cards.m;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class MistformMask extends CardImpl {

    public MistformMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {1}: Enchanted creature becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new MistformMaskEffect(), new GenericManaCost(1)));
    }

    private MistformMask(final MistformMask card) {
        super(card);
    }

    @Override
    public MistformMask copy() {
        return new MistformMask(this);
    }
}

class MistformMaskEffect extends OneShotEffect {

    public MistformMaskEffect() {
        this(false);
    }

    public MistformMaskEffect(boolean nonWall) {
        super(Outcome.BoostCreature);
        staticText = "Enchanted creature becomes the creature type of your choice until end of turn.";
    }

    public MistformMaskEffect(final MistformMaskEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        Permanent enchantedPerm = game.getPermanent(sourcePerm.getAttachedTo());
        if (enchantedPerm == null) {
            return false;
        }
        Effect effect = new BecomesChosenCreatureTypeTargetEffect();
        effect.setTargetPointer(new FixedTarget(enchantedPerm, game));
        return effect.apply(game, source);
    }

    @Override
    public Effect copy() {
        return new MistformMaskEffect(this);
    }

}
