package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObservedStasis extends CardImpl {

    public ObservedStasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, remove enchanted creature from combat. Then draw a card for each tapped creature its controller controls.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ObservedStasisEffect()));

        // Enchanted creature loses all abilities and can't attack or block.
        Ability ability = new SimpleStaticAbility(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA));
        ability.addEffect(new CantAttackBlockAttachedEffect(AttachmentType.AURA).setText("and can't attack or block"));
        this.addAbility(ability);
    }

    private ObservedStasis(final ObservedStasis card) {
        super(card);
    }

    @Override
    public ObservedStasis copy() {
        return new ObservedStasis(this);
    }
}

class ObservedStasisEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    ObservedStasisEffect() {
        super(Outcome.Benefit);
        staticText = "remove enchanted creature from combat. " +
                "Then draw a card for each tapped creature its controller controls";
    }

    private ObservedStasisEffect(final ObservedStasisEffect effect) {
        super(effect);
    }

    @Override
    public ObservedStasisEffect copy() {
        return new ObservedStasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        game.getCombat().removeAttacker(permanent.getId(), game);
        int count = game.getBattlefield().count(filter, permanent.getControllerId(), source, game);
        if (count > 0) {
            Optional.ofNullable(source.getControllerId())
                    .map(game::getPlayer)
                    .ifPresent(player -> player.drawCards(count, source, game));
        }
        return true;
    }
}
