package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaerieDreamthief extends CardImpl {

    public FaerieDreamthief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Faerie Dreamthief enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {2}{B}, Exile Faerie Dreamthief from your graveyard: You draw a card and you lose 1 life.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new DrawCardSourceControllerEffect(1, "you"),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private FaerieDreamthief(final FaerieDreamthief card) {
        super(card);
    }

    @Override
    public FaerieDreamthief copy() {
        return new FaerieDreamthief(this);
    }
}
