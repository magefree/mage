

package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author nantuko
 */
public final class VolitionReins extends CardImpl {

    public VolitionReins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // When Volition Reins enters the battlefield, if enchanted permanent is tapped, untap it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapVolitionReinsEffect()));
        // You control enchanted permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("permanent")));
    }

    private VolitionReins(final VolitionReins card) {
        super(card);
    }

    @Override
    public VolitionReins copy() {
        return new VolitionReins(this);
    }

    public static class UntapVolitionReinsEffect extends OneShotEffect {

        public UntapVolitionReinsEffect() {
            super(Outcome.Untap);
            staticText = "if enchanted permanent is tapped, untap it";
        }

        public UntapVolitionReinsEffect(final UntapVolitionReinsEffect effect) {
            super(effect);
        }

        @Override
        public UntapVolitionReinsEffect copy() {
            return new UntapVolitionReinsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
                if (permanent != null && permanent.isTapped()) {
                    permanent.untap(game);
                    return true;
                }
            }
            return false;
        }

    }

}
