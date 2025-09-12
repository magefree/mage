package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuperState extends CardImpl {

    public SuperState(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has base power and toughness 9/9 and has flying, first strike, trample, and haste.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessAttachedEffect(9, 9, AttachmentType.AURA));
        ability.addEffect(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA).setText(", first strike"));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA).setText(", trample"));
        ability.addEffect(new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA).setText(", and haste"));
        this.addAbility(ability);

        // Whenever enchanted creature deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SuperStateEffect(), "enchanted creature", false,
                true, true, TargetController.OPPONENT
        ));
    }

    private SuperState(final SuperState card) {
        super(card);
    }

    @Override
    public SuperState copy() {
        return new SuperState(this);
    }
}

class SuperStateEffect extends OneShotEffect {

    SuperStateEffect() {
        super(Outcome.Benefit);
        staticText = "it deals that much damage to each other opponent";
    }

    private SuperStateEffect(final SuperStateEffect effect) {
        super(effect);
    }

    @Override
    public SuperStateEffect copy() {
        return new SuperStateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID permanentId = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .orElse(null);
        int damage = SavedDamageValue.MUCH.calculate(game, source, this);
        if (permanentId == null || damage < 1) {
            return false;
        }
        UUID playerId = getTargetPointer().getFirst(game, source);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(opponentId)
                    .filter(uuid -> !uuid.equals(playerId))
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(damage, permanentId, source, game));
        }
        return true;
    }
}
