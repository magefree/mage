package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ArtisanOfForms extends CardImpl {

    public ArtisanOfForms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Artisan of Forms, you may have Artisan of Forms become a copy of target creature, except it has this ability.
        this.addAbility(createAbility());
    }

    private ArtisanOfForms(final ArtisanOfForms card) {
        super(card);
    }

    @Override
    public ArtisanOfForms copy() {
        return new ArtisanOfForms(this);
    }

    static Ability createAbility() {
        Ability ability = new HeroicAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                new ArtisanOfFormsCopyApplier(), true
        ).setText("have {this} become a copy of target creature, except it has this ability"), true);
        ability.addTarget(new TargetCreaturePermanent());
        return ability;
    }
}

class ArtisanOfFormsCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(ArtisanOfForms.createAbility());
        return true;
    }
}
