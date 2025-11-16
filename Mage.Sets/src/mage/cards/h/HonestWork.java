package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HonestWork extends CardImpl {

    public HonestWork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature an opponent controls
        TargetPermanent auraTarget = new TargetOpponentsCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted creature and remove all counters from it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new HonestWorkCountersEffect());
        this.addAbility(ability);

        // Enchanted creature loses all abilities and is a Citizen with base power and toughness 1/1 and "{T}: Add {C}" named Humble Merchant.
        this.addAbility(new SimpleStaticAbility(new HonestWorkAbilityEffect()));
    }

    private HonestWork(final HonestWork card) {
        super(card);
    }

    @Override
    public HonestWork copy() {
        return new HonestWork(this);
    }
}

class HonestWorkCountersEffect extends OneShotEffect {

    HonestWorkCountersEffect() {
        super(Outcome.Benefit);
        staticText = "and remove all counters from it";
    }

    private HonestWorkCountersEffect(final HonestWorkCountersEffect effect) {
        super(effect);
    }

    @Override
    public HonestWorkCountersEffect copy() {
        return new HonestWorkCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable((Permanent) getValue("permanentEnteredBattlefield"))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(permanent -> permanent.removeAllCounters(source, game) > 0)
                .isPresent();
    }
}

class HonestWorkAbilityEffect extends ContinuousEffectImpl {

    HonestWorkAbilityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "enchanted creature loses all abilities and is a Citizen " +
                "with base power and toughness 1/1 and \"{T}: Add {C}\" named Humble Merchant.";
    }

    private HonestWorkAbilityEffect(final HonestWorkAbilityEffect effect) {
        super(effect);
    }

    @Override
    public HonestWorkAbilityEffect copy() {
        return new HonestWorkAbilityEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TextChangingEffects_3:
                permanent.setName("Humble Merchant");
                return true;
            case TypeChangingEffects_4:
                permanent.removeAllCreatureTypes();
                permanent.addSubType(game, SubType.CITIZEN);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                permanent.addAbility(new ColorlessManaAbility(), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer != SubLayer.SetPT_7b) {
                    return false;
                }
                permanent.getPower().setModifiedBaseValue(1);
                permanent.getToughness().setModifiedBaseValue(1);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TextChangingEffects_3:
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
