package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

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
        ManascapeRefractorSpendAnyManaEffect manaEffect = new ManascapeRefractorSpendAnyManaEffect();
        manaEffect.setTargetPointer(new FixedTarget(this.getId()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, manaEffect));
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
        staticText = "{this} all activated abilities of all lands on the battlefield.";
    }

    private ManascapeRefractorGainAbilitiesEffect(final ManascapeRefractorGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm == null) {
            return true;
        }
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof ActivatedAbility) {
                    boolean addAbility = true;
                    for (Ability existingAbility : perm.getAbilities()) {
                        if (existingAbility instanceof RedManaAbility && ability instanceof RedManaAbility) {
                            addAbility = false;
                        } else if (existingAbility instanceof BlueManaAbility && ability instanceof BlueManaAbility) {
                            addAbility = false;
                        } else if (existingAbility instanceof GreenManaAbility && ability instanceof GreenManaAbility) {
                            addAbility = false;
                        } else if (existingAbility instanceof WhiteManaAbility && ability instanceof WhiteManaAbility) {
                            addAbility = false;
                        } else if (existingAbility instanceof BlackManaAbility && ability instanceof BlackManaAbility) {
                            addAbility = false;
                        }
                    }
                    if (addAbility)
                        perm.addAbility(ability, source.getSourceId(), game);
                }
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

    public ManascapeRefractorSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any color to pay the activation costs of {this}'s abilities.";
    }

    public ManascapeRefractorSpendAnyManaEffect(final ManascapeRefractorSpendAnyManaEffect effect) {
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
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            return affectedControllerId.equals(source.getControllerId());
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
