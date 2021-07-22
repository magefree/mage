package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class GnarlwoodDryad extends CardImpl {

    public GnarlwoodDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // <i>Delirium</i> &mdash Gnarlwood Dryad gets +2/+2 as long as there are four or more card types among cards in your graveyard.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; {this} gets +2/+2 as long as there are four or more card types among cards in your graveyard.");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private GnarlwoodDryad(final GnarlwoodDryad card) {
        super(card);
    }

    @Override
    public GnarlwoodDryad copy() {
        return new GnarlwoodDryad(this);
    }
}
