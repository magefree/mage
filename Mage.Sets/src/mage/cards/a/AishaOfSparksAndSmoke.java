package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AishaOfSparksAndSmoke extends CardImpl {

    public AishaOfSparksAndSmoke(UUID ownerId, CardSetInfo setInfo) {
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
                new AishaOfSparksAndSmokeEffect(), false
        ).withFlavorWord("Shoryuken"));
    }

    private AishaOfSparksAndSmoke(final AishaOfSparksAndSmoke card) {
        super(card);
    }

    @Override
    public AishaOfSparksAndSmoke copy() {
        return new AishaOfSparksAndSmoke(this);
    }
}

class AishaOfSparksAndSmokeEffect extends OneShotEffect {

    AishaOfSparksAndSmokeEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a sorcery spell from your hand with mana value " +
                "less than or equal to that damage without paying its mana cost";
    }

    private AishaOfSparksAndSmokeEffect(final AishaOfSparksAndSmokeEffect effect) {
        super(effect);
    }

    @Override
    public AishaOfSparksAndSmokeEffect copy() {
        return new AishaOfSparksAndSmokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(CardType.SORCERY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 1 + (Integer) getValue("damage")));
        return CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(player.getHand()), filter);
    }
}
