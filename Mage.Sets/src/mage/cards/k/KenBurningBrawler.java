package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KenBurningBrawler extends CardImpl {

    public KenBurningBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // {R/W}: Ken gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R/W}")));

        // Shoryuken—Whenever Ken deals combat damage, you may cast a sorcery spell from your hand with mana value less than or equal to that damage without paying its mana cost.
        this.addAbility(new DealsCombatDamageTriggeredAbility(
                new KenBurningBrawlerEffect(), false
        ).withFlavorWord("Shoryuken"));
    }

    private KenBurningBrawler(final KenBurningBrawler card) {
        super(card);
    }

    @Override
    public KenBurningBrawler copy() {
        return new KenBurningBrawler(this);
    }
}

class KenBurningBrawlerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("sorcery spell");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    KenBurningBrawlerEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a sorcery spell from your hand with mana value " +
                "less than or equal to that damage without paying its mana cost";
    }

    private KenBurningBrawlerEffect(final KenBurningBrawlerEffect effect) {
        super(effect);
    }

    @Override
    public KenBurningBrawlerEffect copy() {
        return new KenBurningBrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new CastWithoutPayingManaCostEffect(
                StaticValue.get((Integer) getValue("damage")), filter
        ).apply(game, source);
    }
}
