package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;
import mage.cards.Card;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
enum UnlicensedHearseValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card unlicensedHearse = game.getCard(sourceAbility.getSourceId());
        if (unlicensedHearse == null) {
            return 0;
        }
        // use the source card, not the source object of the ability to grab the correct zcc
        ExileZone cardsExiledWithUnlicensedHearse
                = game.getExile().getExileZone(
                        CardUtil.getExileZoneId(game, unlicensedHearse.getId(), unlicensedHearse.getZoneChangeCounter(game)));
        if (cardsExiledWithUnlicensedHearse == null) {
            return 0;
        }
        return cardsExiledWithUnlicensedHearse.size();
    }

    @Override
    public UnlicensedHearseValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "cards exiled with it";
    }
}

public final class UnlicensedHearse extends CardImpl {

    private static final Hint hint = new ValueHint("Cards exiled", UnlicensedHearseValue.instance);

    public UnlicensedHearse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // {T}: Exile up to two target cards from a single graveyard.
        Ability ability
                = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD_CARDS));
        this.addAbility(ability);

        // Unlicensed Hearse's power and toughness are each equal to the number of cards exiled with it.
        this.addAbility(
                new SimpleStaticAbility(
                        Zone.ALL,
                        new SetPowerToughnessSourceEffect(UnlicensedHearseValue.instance, Duration.EndOfGame))
                        .addHint(hint));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private UnlicensedHearse(final UnlicensedHearse card) {
        super(card);
    }

    @Override
    public UnlicensedHearse copy() {
        return new UnlicensedHearse(this);
    }
}
