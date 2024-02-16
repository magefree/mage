package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;
import mage.game.permanent.token.WhiteAstartesWarriorToken;

/**
 * @author PurpleCrowbar
 */
public final class MarneusCalgar extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public MarneusCalgar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Master Tactician — Whenever one or more tokens enter the battlefield under your control, draw a card.
        //this.addAbility(new MarneusCalgarTriggeredAbility());
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, TargetController.YOU
        ).withFlavorWord("Master Tactician"));

        // Chapter Master — {6}: Create two 2/2 white Astartes Warrior creature tokens with vigilance.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new WhiteAstartesWarriorToken(), 2),
                new ManaCostsImpl<>("{6}")
        ).withFlavorWord("Chapter Master"));
    }

    private MarneusCalgar(final MarneusCalgar card) {
        super(card);
    }

    @Override
    public MarneusCalgar copy() {
        return new MarneusCalgar(this);
    }
}
