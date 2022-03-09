package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerritorialKavu extends CardImpl {

    public TerritorialKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Domain — Territorial Kavu's power and toughness are each equal to the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetPowerToughnessSourceEffect(DomainValue.REGULAR, Duration.EndOfGame)
        ).addHint(DomainHint.instance).setAbilityWord(AbilityWord.DOMAIN));

        // Whenever Territorial Kavu attacks, choose one —
        // • Discard a card. If you do, draw a card.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                null, new DiscardCardCost(), false
        ), false);

        // • Exile up to one target card from a graveyard.
        Mode mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCardInGraveyard(0, 1));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private TerritorialKavu(final TerritorialKavu card) {
        super(card);
    }

    @Override
    public TerritorialKavu copy() {
        return new TerritorialKavu(this);
    }
}
