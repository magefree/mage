package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderMan2099MiguelOHara extends CardImpl {

    public SpiderMan2099MiguelOHara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Spider-Man 2099 enters, return up to one target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private SpiderMan2099MiguelOHara(final SpiderMan2099MiguelOHara card) {
        super(card);
    }

    @Override
    public SpiderMan2099MiguelOHara copy() {
        return new SpiderMan2099MiguelOHara(this);
    }
}
