package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxOfClearSkies extends CardImpl {

    public SphinxOfClearSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Domain -- Whenever Sphinx of Clear Skies deals combat damage to a player, reveal the top X cards of your library, where X is the number of basic land types among lands you control. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RevealAndSeparatePilesEffect(
                DomainValue.REGULAR, TargetController.OPPONENT, TargetController.YOU, Zone.GRAVEYARD
        ).setText("reveal the top X cards of your library, where X is the number of basic land types " +
                "among lands you control. An opponent separates those cards into two piles. " +
                "Put one pile into your hand and the other into your graveyard"), false
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private SphinxOfClearSkies(final SphinxOfClearSkies card) {
        super(card);
    }

    @Override
    public SphinxOfClearSkies copy() {
        return new SphinxOfClearSkies(this);
    }
}
