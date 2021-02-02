package mage.cards.p;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.*;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author noahg
 */
public final class PledgeOfLoyalty extends CardImpl {

    public PledgeOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has protection from the colors of permanents you control. This effect doesn't remove Pledge of Loyalty.
        ProtectionAbility gainedAbility = new PledgeOfLoyaltyProtectionAbility();
        gainedAbility.setAuraIdNotToBeRemoved(this.getId());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Enchanted creature has protection from the colors of permanents you control. This effect doesn't remove {this}.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private PledgeOfLoyalty(final PledgeOfLoyalty card) {
        super(card);
    }

    @Override
    public PledgeOfLoyalty copy() {
        return new PledgeOfLoyalty(this);
    }

    class PledgeOfLoyaltyProtectionAbility extends ProtectionAbility {

        public PledgeOfLoyaltyProtectionAbility() {
            super(new FilterCard());
        }

        public PledgeOfLoyaltyProtectionAbility(final PledgeOfLoyaltyProtectionAbility ability) {
            super(ability);
        }

        @Override
        public PledgeOfLoyaltyProtectionAbility copy() {
            return new PledgeOfLoyaltyProtectionAbility(this);
        }


        @Override
        public boolean canTarget(MageObject source, Game game) {
            ObjectColor color = new ObjectColor();
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(controllerId)) {
                ObjectColor permanentColor = permanent.getColor(game);
                if (permanentColor.isColorless()) {
                    continue;
                }
                if (permanentColor.isBlack()) {
                    color.setBlack(true);
                }
                if (permanentColor.isBlue()) {
                    color.setBlue(true);
                }
                if (permanentColor.isGreen()) {
                    color.setGreen(true);
                }
                if (permanentColor.isRed()) {
                    color.setRed(true);
                }
                if (permanentColor.isWhite()) {
                    color.setWhite(true);
                }
            }

            List<Predicate<MageObject>> colorPredicates = new ArrayList<>();
            if (color.isBlack()) {
                colorPredicates.add(new ColorPredicate(ObjectColor.BLACK));
            }
            if (color.isBlue()) {
                colorPredicates.add(new ColorPredicate(ObjectColor.BLUE));
            }
            if (color.isGreen()) {
                colorPredicates.add(new ColorPredicate(ObjectColor.GREEN));
            }
            if (color.isRed()) {
                colorPredicates.add(new ColorPredicate(ObjectColor.RED));
            }
            if (color.isWhite()) {
                colorPredicates.add(new ColorPredicate(ObjectColor.WHITE));
            }
            Filter protectionFilter = new FilterObject("the colors of permanents you control");
            protectionFilter.add(Predicates.or(colorPredicates));
            this.filter = protectionFilter;
            return super.canTarget(source, game);
        }

        @Override
        public String getRule() {
            return "{this} has protection from the colors of permanents you control.";
        }
    }
}
