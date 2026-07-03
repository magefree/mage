package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author muz
 */
public final class SecretInvasion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(SecretInvasionPredicate.instance);
    }

    public SecretInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Secret Invasion enters, exile up to one other target creature until Secret Invasion leaves the battlefield.
        // Enchanted creature becomes a copy of the exiled card for as long as Secret Invasion remains on the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SecretInvasionEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Enchanted creature has ward {2}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
            new WardAbility(new GenericManaCost(2)), AttachmentType.AURA
        )));
    }

    private SecretInvasion(final SecretInvasion card) {
        super(card);
    }

    @Override
    public SecretInvasion copy() {
        return new SecretInvasion(this);
    }
}

enum SecretInvasionPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
            .ofNullable(input.getSource())
            .map(source -> source.getSourcePermanentOrLKI(game))
            .map(Permanent::getAttachedTo)
            .map(attachedTo -> !attachedTo.equals(input.getObject().getId()))
            .orElse(false);
    }

    @Override
    public String toString() {
        return "other than enchanted creature";
    }
}

class SecretInvasionEffect extends OneShotEffect {

    SecretInvasionEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one other target creature until {this} leaves the battlefield. "
            + "Enchanted creature becomes a copy of the exiled card for as long as {this} remains on the battlefield";
    }

    private SecretInvasionEffect(final SecretInvasionEffect effect) {
        super(effect);
    }

    @Override
    public SecretInvasionEffect copy() {
        return new SecretInvasionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || sourcePermanent.getAttachedTo() == null) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            return true;
        }
        ExileTargetEffect effect = new ExileTargetEffect(
            CardUtil.getCardExileZoneId(game, source), sourcePermanent.getIdName()
        );
        effect.setTargetPointer(new FixedTarget(targetId, game));
        if (!effect.apply(game, source) || game.getState().getZone(targetId) != Zone.EXILED) {
            return false;
        }
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(Zone.BATTLEFIELD), source);
        Card copyCard = game.getCard(targetId);
        if (copyCard == null) {
            return false;
        }
        game.addEffect(new SecretInvasionCopyEffect(copyCard, sourcePermanent.getAttachedTo()), source);
        return true;
    }
}

class SecretInvasionCopyEffect extends CopyEffect {

    SecretInvasionCopyEffect(Card copyCard, UUID enchantedCreatureId) {
        super(Duration.Custom, copyCard, enchantedCreatureId);
    }

    private SecretInvasionCopyEffect(final SecretInvasionCopyEffect effect) {
        super(effect);
    }

    @Override
    public SecretInvasionCopyEffect copy() {
        return new SecretInvasionCopyEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return super.isInactive(source, game) || source.getSourcePermanentIfItStillExists(game) == null;
    }
}
