package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.OmenCard;
import mage.cards.o.Omen;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class FeralDeathgorger extends OmenCard {

    public FeralDeathgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{B}", "Dusk Sight", "{1}{B}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, exile up to two target cards from a single graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 2, new FilterCard("cards")));
        this.addAbility(ability);

        // Dusk Sight
        // Put a +1/+1 counter on up to one target creature. Draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), StaticValue.get(1)));
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.finalizeOmen();
    }

    private FeralDeathgorger(final FeralDeathgorger card) {
        super(card);
    }

    @Override
    public FeralDeathgorger copy() {
        return new FeralDeathgorger(this);
    }
}
