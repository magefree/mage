package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HopelessNightmare extends CardImpl {

    public HopelessNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");


        // When Hopeless Nightmare enters the battlefield, each opponent discards a card and loses 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        ability.addEffect(new LoseLifeOpponentsEffect(2).setText("loses 2 life").concatBy("and"));
        this.addAbility(ability);

        // When Hopeless Nightmare is put into a graveyard from the battlefield, scry 2.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ScryEffect(2, false)));

        // {2}{B}: Sacrifice Hopeless Nightmare.
        this.addAbility(new SimpleActivatedAbility(new SacrificeSourceEffect(), new ManaCostsImpl("{2}{B}")));
    }

    private HopelessNightmare(final HopelessNightmare card) {
        super(card);
    }

    @Override
    public HopelessNightmare copy() {
        return new HopelessNightmare(this);
    }
}
