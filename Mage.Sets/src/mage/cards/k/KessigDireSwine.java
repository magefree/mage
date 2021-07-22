package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class KessigDireSwine extends CardImpl {

    public KessigDireSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // <i>Delirium</i> &mdash; Kessig Dire Swine has trample as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                        DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} has trample as long as there are four or more card types among cards in your graveyard"))
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private KessigDireSwine(final KessigDireSwine card) {
        super(card);
    }

    @Override
    public KessigDireSwine copy() {
        return new KessigDireSwine(this);
    }
}
