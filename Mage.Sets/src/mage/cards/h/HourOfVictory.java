package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HourOfVictory extends CardImpl {

    public HourOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this enchantment enters, create a 2/2 black Zombie creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken())));

        // Max speed -- {1}{B}, Sacrifice this enchantment: Search your library for a card, put it into your hand, then shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private HourOfVictory(final HourOfVictory card) {
        super(card);
    }

    @Override
    public HourOfVictory copy() {
        return new HourOfVictory(this);
    }
}
