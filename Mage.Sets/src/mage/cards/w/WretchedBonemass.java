package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class WretchedBonemass extends CardImpl {

    public WretchedBonemass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, null);
        this.nightCard = true;
        this.color.setBlack(true);

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Wretched Bonemass’s power and toughness are each equal to the total power of the exiled cards used to craft it.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(WretchedBonemassDynamicValue.instance).setText("{this}'s power and toughness are each equal to the total power of the exiled cards used to craft it.")));

        // Wretched Bonemass has flying as long as an exiled card used to craft it has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance.
        this.addAbility(new SimpleStaticAbility(new WretchedBonemassGainAbilityEffect()));

    }

    private WretchedBonemass(final WretchedBonemass card) {
        super(card);
    }

    @Override
    public WretchedBonemass copy() {
        return new WretchedBonemass(this);
    }
}

enum WretchedBonemassDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalPower = 0;
        Permanent permanent = sourceAbility.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return 0;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, permanent.getId(), permanent.getZoneChangeCounter(game) - 2
                ));
        if (exileZone == null) {
            return 0;
        }
        for (Card card : exileZone.getCards(game)) {
            totalPower += card.getPower().getValue();
        }
        return totalPower;
    }

    @Override
    public WretchedBonemassDynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "total power of the exiled cards used to craft it";
    }

    @Override
    public String toString() {
        return "0";
    }
}

class WretchedBonemassGainAbilityEffect extends ContinuousEffectImpl {

    WretchedBonemassGainAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has flying as long as an exiled card used to craft it has flying. The same is true for first strike, double strike, deathtouch, haste, hexproof, indestructible, lifelink, menace, protection, reach, trample, and vigilance.";
    }

    private WretchedBonemassGainAbilityEffect(final WretchedBonemassGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public WretchedBonemassGainAbilityEffect copy() {
        return new WretchedBonemassGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent wretchedBonemass = source.getSourcePermanentIfItStillExists(game);
        if (wretchedBonemass != null) {
            ExileZone exileZone = game
                    .getExile()
                    .getExileZone(CardUtil.getExileZoneId(
                            game, wretchedBonemass.getId(), wretchedBonemass.getZoneChangeCounter(game) - 2
                    ));
            if (exileZone != null
                    && !exileZone.isEmpty()) {
                Set<Card> cardsInExile = exileZone.getCards(game);
                for (Card card : cardsInExile) {
                    for (Ability a : card.getAbilities()) {
                        if (a instanceof FlyingAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof FirstStrikeAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof DoubleStrikeAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof DeathtouchAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof HasteAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof HexproofAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof IndestructibleAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof LifelinkAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof MenaceAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof ProtectionAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof IndestructibleAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof ReachAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof TrampleAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                        if (a instanceof VigilanceAbility) {
                            wretchedBonemass.addAbility(a, source.getSourceId(), game);
                        }
                    }
                }
            }
        }
        return true;
    }
}
