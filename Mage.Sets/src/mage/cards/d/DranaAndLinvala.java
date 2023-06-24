package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DranaAndLinvala extends CardImpl {

    public DranaAndLinvala(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Activated abilities of creatures your opponents control can't be activated.
        this.addAbility(new SimpleStaticAbility(new DranaAndLinvalaCantActivateEffect()));

        // Drana and Linvala has all activated abilities of all creatures your opponents control. You may spend mana as though it were mana of any color to activate those abilities.
        Ability ability = new SimpleStaticAbility(new DranaAndLinvalaGainAbilitiesEffect());
        ability.addEffect(new DranaAndLinvalaManaEffect());
        this.addAbility(ability);
    }

    private DranaAndLinvala(final DranaAndLinvala card) {
        super(card);
    }

    @Override
    public DranaAndLinvala copy() {
        return new DranaAndLinvala(this);
    }
}

class DranaAndLinvalaCantActivateEffect extends RestrictionEffect {

    DranaAndLinvalaCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "activated abilities of creatures your opponents control can't be activated";
    }

    private DranaAndLinvalaCantActivateEffect(final DranaAndLinvalaCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game)
                && game
                .getOpponents(source.getControllerId())
                .contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public DranaAndLinvalaCantActivateEffect copy() {
        return new DranaAndLinvalaCantActivateEffect(this);
    }
}

class DranaAndLinvalaGainAbilitiesEffect extends ContinuousEffectImpl {

    DranaAndLinvalaGainAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creatures your opponents control";
        this.addDependencyType(DependencyType.AddingAbility);
    }

    private DranaAndLinvalaGainAbilitiesEffect(final DranaAndLinvalaGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = source.getSourcePermanentIfItStillExists(game);
        if (perm == null) {
            return false;
        }
        for (Ability ability : game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(permanent -> permanent.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(ability -> ability.getAbilityType() == AbilityType.ACTIVATED
                        || ability.getAbilityType() == AbilityType.MANA)
                .collect(Collectors.toList())) {
            Ability addedAbility = perm.addAbility(ability, source.getSourceId(), game);
            if (addedAbility != null) {
                addedAbility.getEffects().setValue("dranaLinvalaFlag", true);
            }
        }
        return true;
    }

    @Override
    public DranaAndLinvalaGainAbilitiesEffect copy() {
        return new DranaAndLinvalaGainAbilitiesEffect(this);
    }
}

class DranaAndLinvalaManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    DranaAndLinvalaManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any color to activate those abilities";
    }

    private DranaAndLinvalaManaEffect(final DranaAndLinvalaManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DranaAndLinvalaManaEffect copy() {
        return new DranaAndLinvalaManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return CardUtil
                .getMainCardId(game, objectId)
                .equals(source.getSourceId())
                && affectedAbility
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("dranaLinvalaFlag"))
                .filter(Boolean.class::isInstance)
                .anyMatch(Boolean.class::cast)
                && source.isControlledBy(playerId);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
