
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
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
import mage.util.functions.ApplyToPermanent;

/**
 *
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
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new ArtisanOfFormsApplyToPermanent(), true);
        effect.setText("have {this} become a copy of target creature, except it has this ability");
        Ability ability = new HeroicAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ArtisanOfForms(final ArtisanOfForms card) {
        super(card);
    }

    @Override
    public ArtisanOfForms copy() {
        return new ArtisanOfForms(this);
    }
}

class ArtisanOfFormsApplyToPermanent extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        Effect effect = new CopyPermanentEffect(new ArtisanOfFormsApplyToPermanent());
        effect.setText("have {this} become a copy of target creature, except it has this ability");
        mageObject.getAbilities().add(new HeroicAbility(effect, true));
        return true;
    }

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        Effect effect = new CopyPermanentEffect(new ArtisanOfFormsApplyToPermanent());
        effect.setText("have {this} become a copy of target creature, except it has this ability");
        permanent.addAbility(new HeroicAbility(effect, true), game);
        return true;
    }

}
