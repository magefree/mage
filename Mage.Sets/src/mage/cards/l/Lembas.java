package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Lembas extends CardImpl {

    public Lembas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.FOOD);

        // When Lembas enters the battlefield, scry 1, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice Lembas: You gain 3 life.
        ability = new SimpleActivatedAbility(new GainLifeEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // When Lembas is put into a graveyard from the battlefield, its owner shuffles it into their library.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new ShuffleIntoLibrarySourceEffect().setText("its owner shuffles it into their library")
        ));
    }

    private Lembas(final Lembas card) {
        super(card);
    }

    @Override
    public Lembas copy() {
        return new Lembas(this);
    }
}
