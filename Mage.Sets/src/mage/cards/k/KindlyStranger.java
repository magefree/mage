package mage.cards.k;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KindlyStranger extends CardImpl {

    public KindlyStranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.d.DemonPossessedWitch.class;

        // <i>Delirium</i> &mdash; {2}{B}: Transform Kindly Stranger. Activate this ability only if there are four or more card types among cards in your graveyard.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}"),
                DeliriumCondition.instance, "<i>Delirium</i> &mdash; {2}{B}: Transform {this}. " +
                "Activate only if there are four or more card types among cards in your graveyard."
        ).addHint(CardTypesInGraveyardHint.YOU));
    }

    private KindlyStranger(final KindlyStranger card) {
        super(card);
    }

    @Override
    public KindlyStranger copy() {
        return new KindlyStranger(this);
    }
}
