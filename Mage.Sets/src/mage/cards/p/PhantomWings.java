
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PhantomWings extends CardImpl {

    public PhantomWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)));
        // Sacrifice Phantom Wings: Return enchanted creature to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhantomWingsReturnEffect(), new SacrificeSourceCost()));
        
    }

    public PhantomWings(final PhantomWings card) {
        super(card);
    }

    @Override
    public PhantomWings copy() {
        return new PhantomWings(this);
    }

    private static class PhantomWingsReturnEffect extends OneShotEffect {

        public PhantomWingsReturnEffect() {
            super(Outcome.ReturnToHand);
            staticText = "Return enchanted creature to its owner's hand";
        }

        public PhantomWingsReturnEffect(final PhantomWingsReturnEffect effect) {
            super(effect);
        }

        @Override
        public PhantomWingsReturnEffect copy() {
            return new PhantomWingsReturnEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent != null && permanent.getAttachedTo() != null)
            {
                Permanent enchantedCreature = game.getPermanent(permanent.getAttachedTo());
                if (enchantedCreature != null) {
                    return enchantedCreature.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }        
            return false;
        }
    }
}