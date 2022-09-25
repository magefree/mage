package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author AsterAether
 */
public final class ManascapeRefractor extends CardImpl {

    public ManascapeRefractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Manascape Refractor enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Manascape Refractor has all activated abilities of all lands on the battlefield.
        this.addAbility(new SimpleStaticAbility(new ManascapeRefractorGainAbilitiesEffect()));

        // You may spend mana as though it were mana of any color to pay the activation costs of Manascape Refractor's abilities.
        this.addAbility(new SimpleStaticAbility(new ManascapeRefractorSpendAnyManaEffect()));
    }

    private ManascapeRefractor(final ManascapeRefractor card) {
        super(card);
    }

    @Override
    public ManascapeRefractor copy() {
        return new ManascapeRefractor(this);
    }
}

class ManascapeRefractorGainAbilitiesEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    ManascapeRefractorGainAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all lands on the battlefield.";
        this.addDependencyType(DependencyType.AddingAbility);
    }

    private ManascapeRefractorGainAbilitiesEffect(final ManascapeRefractorGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null) {
            return false;
        }
        for (Ability ability : game.getState()
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(permanent -> permanent.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(ability -> ability.getAbilityType() == AbilityType.ACTIVATED
                        || ability.getAbilityType() == AbilityType.MANA)
                .collect(Collectors.toList())) {
            // optimization to disallow the adding of duplicate, unnecessary basic mana abilities
            if (!(ability instanceof BasicManaAbility)
                    || perm.getAbilities(game)
                    .stream()
                    .noneMatch(ability.getClass()::isInstance)) {
                perm.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public ManascapeRefractorGainAbilitiesEffect copy() {
        return new ManascapeRefractorGainAbilitiesEffect(this);
    }
}

class ManascapeRefractorSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    ManascapeRefractorSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any color to pay the activation costs of {this}'s abilities.";
    }

    private ManascapeRefractorSpendAnyManaEffect(final ManascapeRefractorSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ManascapeRefractorSpendAnyManaEffect copy() {
        return new ManascapeRefractorSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        return objectId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
