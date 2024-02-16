package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MartialImpetus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(MartialImpetusPredicate.instance);
    }

    public MartialImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and is goaded.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GoadAttachedEffect());
        this.addAbility(ability);

        // Whenever enchanted creature attacks, each other creature that's attacking one of your opponents gets +1/+1 until end of turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, true).setText(
                        "each other creature that's attacking one of your opponents gets +1/+1 until end of turn"
                ), AttachmentType.AURA, false)
        );
    }

    private MartialImpetus(final MartialImpetus card) {
        super(card);
    }

    @Override
    public MartialImpetus copy() {
        return new MartialImpetus(this);
    }
}

enum MartialImpetusPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent martialImpetus = input.getSource().getSourcePermanentOrLKI(game);
        if (martialImpetus != null) {
            Permanent attachedTo = game.getPermanentOrLKIBattlefield(martialImpetus.getAttachedTo());
            UUID auraControllerId = martialImpetus.getControllerId();
            if (attachedTo != null // check the creature that the aura is attached to, not the aura itself
                    && input.getObject() != null // creature being checked for predicate
                    && input.getObject() != attachedTo // must be other creature
                    && input.getObject().isAttacking() // attacking
                    && game.getOpponents(auraControllerId) // check for opponents of aura's controller
                    .contains(game.getCombat().getDefendingPlayerId(input.getObject().getId(), game))) {
                return true;
            }
        }
        return false;
    }
}
