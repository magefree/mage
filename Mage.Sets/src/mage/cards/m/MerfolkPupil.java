package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkPupil extends CardImpl {

    public MerfolkPupil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Merfolk Pupil enters the battlefield, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1)
        ));

        // {1}{U}, Exile Merfolk Pupil from your graveyard: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new DrawDiscardControllerEffect(1, 1),
                new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private MerfolkPupil(final MerfolkPupil card) {
        super(card);
    }

    @Override
    public MerfolkPupil copy() {
        return new MerfolkPupil(this);
    }
}
