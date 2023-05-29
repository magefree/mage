package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Soulflayer extends CardImpl {

    public Soulflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Delve
        this.addAbility(new DelveAbility());

        // If a creature card with flying was exiled with Soulflayer's delve ability, Soulflayer has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, reach, trample, and vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SoulflayerEffect()));

    }

    private Soulflayer(final Soulflayer card) {
        super(card);
    }

    @Override
    public Soulflayer copy() {
        return new Soulflayer(this);
    }
}

class SoulflayerEffect extends ContinuousEffectImpl {

    private Set<Ability> abilitiesToAdd;
    private MageObjectReference objectReference = null;

    public SoulflayerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "If a creature card with flying was exiled with {this}'s delve ability, {this} has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, reach, trample, and vigilance";
        abilitiesToAdd = null;
    }

    public SoulflayerEffect(final SoulflayerEffect effect) {
        super(effect);
        if (effect.abilitiesToAdd != null) {
            this.abilitiesToAdd = new HashSet<>();
            for (Ability a : effect.abilitiesToAdd) {
                this.abilitiesToAdd.add(a.copy());
            }
        }
        this.objectReference = effect.objectReference;
    }

    @Override
    public SoulflayerEffect copy() {
        return new SoulflayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            // one time abilities collect
            if (objectReference == null || !objectReference.refersTo(permanent, game)) {
                abilitiesToAdd = new HashSet<>();
                this.objectReference = new MageObjectReference(permanent, game);
                String keyString = CardUtil.getCardZoneString("delvedCards", source.getSourceId(), game, true);
                Cards delvedCards = (Cards) game.getState().getValue(keyString);
                if (delvedCards != null) {
                    for (Card card : delvedCards.getCards(game)) {
                        if (!card.isCreature(game)) {
                            continue;
                        }
                        for (Ability cardAbility : card.getAbilities(game)) {
                            if (cardAbility instanceof FlyingAbility) {
                                abilitiesToAdd.add(FlyingAbility.getInstance());
                            }
                            if (cardAbility instanceof FirstStrikeAbility) {
                                abilitiesToAdd.add(FirstStrikeAbility.getInstance());
                            }
                            if (cardAbility instanceof DoubleStrikeAbility) {
                                abilitiesToAdd.add(DoubleStrikeAbility.getInstance());
                            }
                            if (cardAbility instanceof DeathtouchAbility) {
                                abilitiesToAdd.add(DeathtouchAbility.getInstance());
                            }
                            if (cardAbility instanceof HasteAbility) {
                                abilitiesToAdd.add(HasteAbility.getInstance());
                            }
                            if (cardAbility instanceof HexproofBaseAbility) {
                                abilitiesToAdd.add(HexproofAbility.getInstance());
                            }
                            if (cardAbility instanceof IndestructibleAbility) {
                                abilitiesToAdd.add(IndestructibleAbility.getInstance());
                            }
                            if (cardAbility instanceof LifelinkAbility) {
                                abilitiesToAdd.add(LifelinkAbility.getInstance());
                            }
                            if (cardAbility instanceof ReachAbility) {
                                abilitiesToAdd.add(ReachAbility.getInstance());
                            }
                            if (cardAbility instanceof TrampleAbility) {
                                abilitiesToAdd.add(TrampleAbility.getInstance());
                            }
                            if (cardAbility instanceof VigilanceAbility) {
                                abilitiesToAdd.add(VigilanceAbility.getInstance());
                            }
                        }
                    }
                }
            }

            // all time abilities apply
            for (Ability ability : abilitiesToAdd) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
            return true;
        } else if (abilitiesToAdd != null) {
            abilitiesToAdd = null;
        }
        return false;
    }
}
