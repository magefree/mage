package mage.cards.s;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author NinthWorld
 */
public final class SporeCrawler extends CardImpl {

    public SporeCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Spore Crawler enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost()));

        // {1}{G}: Spore Crawler becomes a 1/3 green Zerg creature with reach until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(1, 3, "1/3 green Zerg creature with reach", SubType.ZERG)
                                .withColor("G")
                                .withAbility(ReachAbility.getInstance()),
                        "land", Duration.EndOfTurn, false, false),
                new ManaCostsImpl("{1}{G}")));
    }

    public SporeCrawler(final SporeCrawler card) {
        super(card);
    }

    @Override
    public SporeCrawler copy() {
        return new SporeCrawler(this);
    }
}
