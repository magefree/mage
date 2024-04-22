package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class PristineSkywise extends CardImpl {

    public PristineSkywise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a noncreature spell, untap Pristine Skywise. It gains protection from the color of your choice until the end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addEffect(new GainProtectionFromColorSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private PristineSkywise(final PristineSkywise card) {
        super(card);
    }

    @Override
    public PristineSkywise copy() {
        return new PristineSkywise(this);
    }
}
