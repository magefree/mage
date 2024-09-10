package mage.cards.c;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CynicalLoner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.GLIMMER, "Glimmers");

    public CynicalLoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Cynical Loner can't be blocked by Glimmers.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Survival -- At the beginning of your second main phase, if Cynical Loner is tapped, you may search your library for a card, put it into your graveyard, then shuffle.
        this.addAbility(new SurvivalAbility(new SearchLibraryPutInGraveyardEffect(false)));
    }

    private CynicalLoner(final CynicalLoner card) {
        super(card);
    }

    @Override
    public CynicalLoner copy() {
        return new CynicalLoner(this);
    }
}
