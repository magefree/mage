package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TrooperToken;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author NinthWorld
 */
public final class CaptainPhasma extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Trooper creatures");
    private static final FilterCreatureCard filterCard = new FilterCreatureCard("Trooper creature card");

    static {
        filter.add(SubType.TROOPER.getPredicate());
        filter.add(TokenPredicate.FALSE);
        filterCard.add(SubType.TROOPER.getPredicate());
    }

    public CaptainPhasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Nontoken Trooper creatures you control have "When this creature enters the battlefield, create 1/1/ white Trooper creature token."
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TrooperToken()), false)
                .setTriggerPhrase("When this creature enters the battlefield, ");
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter, false)));

        // {W}{U}{B}{R}{G}: Search your library for a Trooper creature card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
    }

    private CaptainPhasma(final CaptainPhasma card) {
        super(card);
    }

    @Override
    public CaptainPhasma copy() {
        return new CaptainPhasma(this);
    }
}
