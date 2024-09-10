package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RebbecArchitectOfAscension extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("each mana value among artifacts you control");

    static {
        filter.add(RebbecArchitectOfAscensionPredicate.instance);
    }

    public RebbecArchitectOfAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Artifacts you control have protection from each converted mana cost among artifacts you control.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ProtectionAbility(filter), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACTS
        )));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private RebbecArchitectOfAscension(final RebbecArchitectOfAscension card) {
        super(card);
    }

    @Override
    public RebbecArchitectOfAscension copy() {
        return new RebbecArchitectOfAscension(this);
    }
}

enum RebbecArchitectOfAscensionPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                        game.getControllerId(input.getSourceId()), input.getSource(), game
                ).stream()
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .anyMatch(n -> input.getObject().getManaValue() == n);
    }
}
