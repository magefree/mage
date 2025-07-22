package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.permanent.AnotherEnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PainForAll extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterAnyTarget("any other target");

    static {
        filter.getPermanentFilter().add(AnotherEnchantedPredicate.instance);
    }

    public PainForAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, enchanted creature deals damage equal to its power to any other target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PainForAllFirstEffect());
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);

        // Whenever enchanted creature is dealt damage, it deals that much damage to each opponent.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(
                Zone.BATTLEFIELD, new PainForAllSecondEffect(), false, SetTargetPointer.PERMANENT
        ));
    }

    private PainForAll(final PainForAll card) {
        super(card);
    }

    @Override
    public PainForAll copy() {
        return new PainForAll(this);
    }
}

class PainForAllFirstEffect extends OneShotEffect {

    PainForAllFirstEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted creature deals damage equal to its power to any other target";
    }

    private PainForAllFirstEffect(final PainForAllFirstEffect effect) {
        super(effect);
    }

    @Override
    public PainForAllFirstEffect copy() {
        return new PainForAllFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(permanent -> game.damagePlayerOrPermanent(
                        getTargetPointer().getFirst(game, source),
                        permanent.getPower().getValue(), permanent.getId(),
                        source, game, false, true
                ) > 0)
                .isPresent();
    }
}

class PainForAllSecondEffect extends OneShotEffect {

    PainForAllSecondEffect() {
        super(Outcome.Benefit);
        staticText = "it deals that much damage to each opponent";
    }

    private PainForAllSecondEffect(final PainForAllSecondEffect effect) {
        super(effect);
    }

    @Override
    public PainForAllSecondEffect copy() {
        return new PainForAllSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID permanentId = this.getTargetPointer().getFirst(game, source);
        int damage = (Integer) this.getValue("damage");
        if (permanentId == null || damage < 1) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(opponentId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(damage, permanentId, source, game));
        }
        return true;
    }
}
