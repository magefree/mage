package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class DeathcapCultivator extends CardImpl {

    public DeathcapCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // <i>Delirium</i> &mdash; Deathcap Cultivator has deathtouch as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield),
                        DeliriumCondition.instance, "<i>Delirium</i> &mdash; {this} has deathtouch as long as there are four or more card types among cards in your graveyard"))
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private DeathcapCultivator(final DeathcapCultivator card) {
        super(card);
    }

    @Override
    public DeathcapCultivator copy() {
        return new DeathcapCultivator(this);
    }
}
