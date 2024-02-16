package mage.cards.p;

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
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhantomWings extends CardImpl {

    public PhantomWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        )));

        // Sacrifice Phantom Wings: Return enchanted creature to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new PhantomWingsReturnEffect(), new SacrificeSourceCost()));
    }

    private PhantomWings(final PhantomWings card) {
        super(card);
    }

    @Override
    public PhantomWings copy() {
        return new PhantomWings(this);
    }

    private static final class PhantomWingsReturnEffect extends OneShotEffect {

        private PhantomWingsReturnEffect() {
            super(Outcome.ReturnToHand);
            staticText = "Return enchanted creature to its owner's hand";
        }

        private PhantomWingsReturnEffect(final PhantomWingsReturnEffect effect) {
            super(effect);
        }

        @Override
        public PhantomWingsReturnEffect copy() {
            return new PhantomWingsReturnEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            Permanent permanent = source.getSourcePermanentOrLKI(game);
            if (player == null || permanent == null || permanent.getAttachedTo() == null) {
                return false;
            }
            Permanent enchantedCreature = game.getPermanent(permanent.getAttachedTo());
            return enchantedCreature != null && player.moveCards(enchantedCreature, Zone.HAND, source, game);
        }
    }
}
